package at.ac.fhcampuswien.newsanalyzer.ctrl;

import at.ac.fhcampuswien.newsapi.NewsApi;
import at.ac.fhcampuswien.newsapi.NewsApiBuilder;
import at.ac.fhcampuswien.newsapi.NewsApiException;
import at.ac.fhcampuswien.newsapi.beans.Article;
import at.ac.fhcampuswien.newsapi.beans.NewsResponse;
import at.ac.fhcampuswien.newsapi.enums.Country;
import at.ac.fhcampuswien.newsapi.enums.SortBy;
import jdk.javadoc.internal.doclets.toolkit.util.Comparators;

import java.util.*;
import java.util.stream.Collectors;

import static at.ac.fhcampuswien.newsapi.enums.Endpoint.TOP_HEADLINES;

public class Controller {

	public static final String APIKEY = "71c939f4ad9f47fbac7ff5b79366fc6d";  //TODO add your api key

	public void process(String searchWord) {
		System.out.println("Start process");

		//TODO implement Error handling
		try {
			if (searchWord.isEmpty())
				throw new NewsApiException("Search word is empty!");

			//TODO load the news based on the parameters
			NewsApi newsApi = new NewsApiBuilder()
					.setApiKey(APIKEY)
					.setQ(searchWord)
					.setEndPoint(TOP_HEADLINES)
					.setSortBy(SortBy.RELEVANCY)
					.setSourceCountry(Country.at)
					.createNewsApi();

			NewsResponse newsResponse = newsApi.getNews();

			if (newsResponse != null) {
				List<Article> articles = newsResponse.getArticles();
				articles.stream().forEach(article -> System.out.println(article.toString()));
			} else {
				throw new NewsApiException("JSON Responce is empty!");
			}

			//TODO implement methods for analysis
			//Number of articles found
			List<Article> articles = newsResponse.getArticles();
			System.out.println("\nNumber of articles for your search is " + articles.size());


			String prov = articles.stream()
					.collect(Collectors.groupingBy(article -> article.getSource().getName(), Collectors.counting()))
					.entrySet().stream()
					.max(Comparator.comparingInt(t -> t.getValue().intValue()))
					.get()
					.getKey();

			if (prov != null)
				System.out.println("Provider delivers the most articles: " + prov);
			else
				System.out.println("There is no provider");


			String authorsName = articles.stream()
					.filter(article -> Objects.nonNull(article.getAuthor()))
					.min(Comparator.comparingInt(article -> article.getAuthor().length()))
					.get()
					.getAuthor();

			if (authorsName != null)
				System.out.println("The shortest author's name is \" + authorsName");
			else
				System.out.println("There is no authorsName");



			List<Article> sortArticles = articles.stream()
					.sorted(Comparator.comparingInt(article -> article.getTitle().length()))
					.sorted(Comparator.comparing(Article::getTitle))
					.collect(Collectors.toList());

			if (sortArticles != null)
				System.out.println("There is the longest title!");
			else
				System.out.println("Nothing to show");


		} catch (NewsApiException e) {
			System.out.println(e.getMessage());
		} finally {
			System.out.println("End process");
		}
	}


	public Object getData() {

		return null;
	}
}