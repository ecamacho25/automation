package generators;

import okhttp3.*;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.io.FileWriter;
import java.io.IOException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import base.BaseTest;
import utils.Config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.json.JSONArray;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.nio.charset.StandardCharsets;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.concurrent.TimeUnit;

public class GeneratePOMClassesByUrl extends BaseTest {

	protected static WebDriver driver;
	private static final String API_URL_OPENIA = Config.getStringProperty("openAIApiUrl");
	private static final String API_KEY_OPENIA = Config.getStringProperty("openAIApiKey");
	private static final String outputDirPages = Config.getStringProperty("pathPagesFolder");
	private static final String promptFilePath = Config.getStringProperty("promptFilePath");
	private static final String urlTargetToMapped = "https://parabank.parasoft.com/parabank/register.htm";

	public static void createSeleniumPageFromHtml(String html) throws IOException {

		OkHttpClient client = new OkHttpClient.Builder()
				.connectTimeout(Config.getIntProperty("defaultTimeoutOkHttpClientConnection"), TimeUnit.SECONDS)
				.readTimeout(Config.getIntProperty("defaultTimeoutOkHttpClientRead"), TimeUnit.SECONDS)
				.writeTimeout(Config.getIntProperty("defaultTimeoutOkHttpClientWrite"), TimeUnit.SECONDS).build();

		String filteredHtml = extractInteractiveElements(html);
		String promptTemplate = readPromptFromFile(promptFilePath);
		String finalPrompt = promptTemplate + "\n\n" + filteredHtml;

		Request request = createRequestOpenAIApi(finalPrompt);

		try (Response response = client.newCall(request).execute()) {
			if (!response.isSuccessful()) {
				String errorResponseBody = response.body().string();
				throw new IOException("Unexpected code " + response + " with body " + errorResponseBody);
			}
			JSONObject responseBody = new JSONObject(response.body().string());
			String javaCode = responseBody.getJSONArray("choices").getJSONObject(0).getJSONObject("message")
					.getString("content").trim();

			createJavaClassFromAIResponse(javaCode);

		}
	}

	private static Request createRequestOpenAIApi(String prompt) {
		JSONObject message = new JSONObject();
		message.put("role", "user");
		message.put("content", prompt);

		JSONArray messages = new JSONArray();
		messages.put(message);

		JSONObject json = new JSONObject();
		json.put("model", "gpt-3.5-turbo");
		json.put("messages", messages);
		json.put("max_tokens", 1500);
		json.put("temperature", 0.5);

		RequestBody body = RequestBody.create(json.toString(), MediaType.parse("application/json"));
		Request request = new Request.Builder().url(API_URL_OPENIA).post(body)
				.addHeader("Authorization", "Bearer " + API_KEY_OPENIA).build();

		return request;
	}

	private static void createJavaClassFromAIResponse(String aiJavaCode) throws IOException {
		String className = extractClassName(aiJavaCode);

		if (className != null) {
			Path outputPath = Paths.get(outputDirPages, className + ".java");
			saveToFile(outputPath, aiJavaCode);
		} else {
			System.err.println("The class name could not be found in the generated code.");
		}
	}

	private static String extractInteractiveElements(String html) {
		Document doc = Jsoup.parse(html);
		Elements interactiveElements = doc.select("a, button, input, select, textarea, form");
		StringBuilder filteredHtml = new StringBuilder();
		for (Element element : interactiveElements) {
			filteredHtml.append(element.outerHtml()).append("\n");
		}
		return filteredHtml.toString();
	}

	private static String extractClassName(String javaCode) {
		Pattern pattern = Pattern.compile("public class\\s+(\\w+)");
		Matcher matcher = pattern.matcher(javaCode);
		if (matcher.find()) {
			return matcher.group(1);
		}
		return null;
	}

	private static void saveToFile(Path path, String content) throws IOException {
		Files.createDirectories(path.getParent());
		try (FileWriter fileWriter = new FileWriter(path.toFile())) {
			fileWriter.write(content);
		}
	}

	public static String getHtml(String url) {
		switch (Config.getStringProperty("browser").toLowerCase()) {
        case "chrome":
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            break;
        case "edge":
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
            break;
        case "firefox":
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
            break;
		}
		driver.get(url);
		String pageSource = driver.getPageSource();
		driver.quit();
		return pageSource;
	}

	private static String readPromptFromFile(String filePath) throws IOException {
		return Files.readString(Path.of(filePath), StandardCharsets.UTF_8);
	}

	public static void main(String[] args) {

		String html = getHtml(urlTargetToMapped);
		try {
			createSeleniumPageFromHtml(html);
			System.out.println(
					"Generated Java Class, please refresh your project and check the folder " + outputDirPages);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

