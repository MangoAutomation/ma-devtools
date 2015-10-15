/**
 * Copyright (C) 2014 Infinite Automation Software. All rights reserved.
 * @author Terry Packer
 */
package com.infiniteautomation;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringEscapeUtils;
import org.kohsuke.github.GHIssue;
import org.kohsuke.github.GHIssueState;
import org.kohsuke.github.GHMilestone;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;

public class IssuesDownload {

    public static void main(String[] args) {
    	saveIssues("infiniteautomation", "ma-core-public", GHIssueState.OPEN, "2.6.0 Release");
    	saveIssues("infiniteautomation", "ma-core-public", GHIssueState.CLOSED, "2.6.0 Release");
    	
    }

    public static String saveIssues(String username, String repositoryURL, GHIssueState issueState, String milestoneTitle) {

        try {
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	Properties config = new Properties();
            config.load(IssuesDownload.class.getResourceAsStream("/env.properties"));

            String githubtoken = config.getProperty("github.token");
        	
            GitHub github = GitHub.connectUsingOAuth(githubtoken);
            GHUser user = github.getUser(username);
//            for(GHRepository repo : user.listRepositories()){
//            	System.out.println(repo.getFullName());
//            }
            
            GHRepository repository = user.getRepository(repositoryURL);
            FileWriter writer = new FileWriter(repositoryURL + "-" + issueState + ".csv");
            writer.append("Id, Title, Body, Creator, Assignee, Milestone, State, Date Closed");
            writer.append("\n");
            GHMilestone forMilestone = null;
            
            for(GHMilestone ms : repository.listMilestones(GHIssueState.OPEN)){
            	if(ms.getTitle().equalsIgnoreCase(milestoneTitle)){
            		forMilestone = ms;
            		break;
            	}
            }
            
            List<GHIssue> issues;
            if(forMilestone == null){
            	issues = repository.getIssues(issueState);	
            }else{
            	issues = repository.getIssues(issueState, forMilestone);
            }
            
            for (GHIssue issue : issues) {
            	
                writer.append(String.valueOf(issue.getNumber()) + ",");
                writer.append(StringEscapeUtils.escapeCsv(issue.getTitle()) + ",");
                writer.append(StringEscapeUtils.escapeCsv(issue.getBody()) + ",");
                writer.append(issue.getUser().getLogin() + ",");
                if (issue.getAssignee() != null) {
                    writer.append(issue.getAssignee().getName() + ",");
                } else {
                    writer.append(" ,");
                }
                if (issue.getMilestone() != null) {
                    writer.append(issue.getMilestone().getTitle() + ",");
                } else {
                    writer.append(" ,");
                }
                writer.append(issue.getState() + ",");
                String closeDate = "";
                if(issue.getClosedAt() != null)
                	closeDate = sdf.format(issue.getClosedAt());
                writer.append(closeDate + ",");
                writer.append("\n");
            }
            writer.flush();
            writer.close();
            return "Download Complete!";
        } catch (IOException ex) {
            System.out.println("An IOException has occured!");
            ex.printStackTrace();
            if (ex.getMessage().equalsIgnoreCase("api.github.com")) {
            return "An error has occurred reaching " + ex.getMessage() + "! Please check your network connection.";
            }
        }
        return "An error has occured!";
    }
}
