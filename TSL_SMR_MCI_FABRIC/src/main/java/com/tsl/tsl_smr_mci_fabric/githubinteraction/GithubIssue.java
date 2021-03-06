package com.tsl.tsl_smr_mci_fabric.githubinteraction;

import java.io.IOException;
import java.util.Arrays;

import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.Label;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.IssueService;
import org.eclipse.egit.github.core.service.LabelService;

import com.tsl.tsl_smr_mci_fabric.ModLogger;

public interface GithubIssue {

	// Make new issue with arguments: title,body,label
	public static int create(String title, String body, String label) {
		// sets up GitHub Client
		GitHubClient client = new GitHubClient();
		client.setOAuth2Token(GithubInfo.TOKEN);

		// Starts GitHub services
		IssueService issueService = new IssueService(client);
		LabelService labelService = new LabelService(client);

		try {
			Issue issue = new Issue();

			// Sets title and body of issue
			issue.setTitle(title);
			issue.setBody(body);

			Label addLabel = labelService.getLabel(GithubInfo.GITHUB_BOT_NAME, GithubInfo.GITHUB_REPOSITORY, label);
			issue.setLabels(Arrays.asList(addLabel));

			// Creates Issue and returns ID
			return issueService.createIssue(GithubInfo.GITHUB_BOT_NAME, GithubInfo.GITHUB_REPOSITORY, issue).getNumber();

		} catch (Exception e) {
			// Error Handler
			ModLogger.LOGGER.error("Failed To Create Github Issue -> " + e.getStackTrace());
			return -1;
		}
	}
	
	public static String getStatus(int issueNum) {
		// sets up GitHub Client
		GitHubClient client = new GitHubClient();
		client.setOAuth2Token(GithubInfo.TOKEN);
		
		// Starts GitHub services
		IssueService issueService = new IssueService(client);
		
		try {
			//get GitHub Issue from the issue number
			Issue issue = issueService.getIssue(GithubInfo.GITHUB_BOT_NAME, GithubInfo.GITHUB_REPOSITORY, issueNum);
			
			//return issue state
			return issue.getState();
			
			
		} catch (IOException e) {
			// Error handler
			ModLogger.LOGGER.error("Failed To Get Issue -> " + e.getStackTrace());
			return null;
		}	
	}
	
	public static String getUrl(int issueNum) {
		// sets up GitHub Client
		GitHubClient client = new GitHubClient();
		client.setOAuth2Token(GithubInfo.TOKEN);
				
		// Starts GitHub services
		IssueService issueService = new IssueService(client);
				
		try {
			//get GitHub Issue from the issue number
			Issue issue = issueService.getIssue(GithubInfo.GITHUB_BOT_NAME, GithubInfo.GITHUB_REPOSITORY, issueNum);
			
			//return issue URL
			return issue.getUrl();
			
			
		} catch (IOException e) {
			// Error handler
			ModLogger.LOGGER.error("Failed To Get Issue -> " + e.getStackTrace());
			return null;
		}	
	}
}
