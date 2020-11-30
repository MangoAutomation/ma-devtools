/**
 * Copyright (C) 2014 Infinite Automation Software. All rights reserved.
 * @author Terry Packer
 */
package com.infiniteautomation;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.kohsuke.github.GHIssue;
import org.kohsuke.github.GHIssueComment;
import org.kohsuke.github.GHOrganization;
import org.kohsuke.github.GHProject;
import org.kohsuke.github.GHProjectCard;
import org.kohsuke.github.GHProjectColumn;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

/**
 * Generate a Jira compatible list of issues
 *
 * See: https://confluence.atlassian.com/adminjiraserver/importing-data-from-csv-938847533.html#ImportingdatafromCSV-how
 *
 * @author Terry Packer
 */
public class IssuesFromProjectDownload {

    private static final String[] JIRA_HEADERS = {
            "Summary",
            "Description",
            "Comment",
            "Labels"
    };

    public static void main(String[] args) {
        saveIssuesFromProject("infiniteautomation", "Mango Project Board", "Post 4.0 Release");
    }

    public static String saveIssuesFromProject(String organization, String project, String column) {

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Properties config = new Properties();
            config.load(IssuesDownload.class.getResourceAsStream("/env.properties"));

            String githubtoken = config.getProperty("github.token");

            GitHub github = GitHub.connectUsingOAuth(githubtoken);

            Map<String, GHOrganization> orgs = github.getMyOrganizations();

            GHOrganization ias = orgs.get(organization);

            FileWriter writer = new FileWriter(project  + ".csv");
            for(String header : JIRA_HEADERS) {
                writer.append(header);
                if(!StringUtils.equals(JIRA_HEADERS[JIRA_HEADERS.length - 1], header)) {
                    writer.append(",");
                }
            }
            writer.append("\n");

            for(GHProject p : ias.listProjects()) {
                if(StringUtils.equals(p.getName(), project)) {
                    //Found our project
                    for(GHProjectColumn c : p.listColumns()) {
                        if(StringUtils.equals(c.getName(), column)) {
                            for(GHProjectCard card : c.listCards()) {
                                //Format: https://api.github.com/repos/infiniteautomation/ma-modules-proprietary/issues/91
                                if(card.getContentUrl() != null) {
                                    String[] parts = card.getContentUrl().getPath().split("/");

                                    String repository = parts[parts.length - 3];
                                    int issueId = Integer.parseInt(parts[parts.length - 1]);
                                    GHRepository ourRepo = ias.getRepository(repository);
                                    GHIssue issue = ourRepo.getIssue(issueId);

                                    //Summary
                                    writer.append(StringEscapeUtils.escapeCsv(issue.getTitle()) + ",");

                                    //Description
                                    writer.append(StringEscapeUtils.escapeCsv(issue.getBody()) + ",");

                                    //Comments
                                    StringBuilder commentBuilder = new StringBuilder();
                                    for(GHIssueComment comment : issue.getComments()) {
                                        commentBuilder.append(comment.getUser().getName());
                                        commentBuilder.append(": ");
                                        commentBuilder.append(sdf.format(comment.getCreatedAt()));
                                        commentBuilder.append(" - ");
                                        commentBuilder.append(StringEscapeUtils.escapeCsv(comment.getBody()));
                                        commentBuilder.append("\n");
                                    }
                                    writer.append(StringEscapeUtils.escapeCsv(commentBuilder.toString()));

                                    //Add Label
                                    writer.append(",Mango,");
                                    writer.append("\n");
                                }else {
                                    //This is a card (not an issue)
                                    //Summary
                                    writer.append(StringEscapeUtils.escapeCsv(card.getNote()) + ",");

                                    //No Description
                                    writer.append(",");

                                    //No comments

                                    //Add Label
                                    writer.append(",Mango,");
                                    writer.append("\n");
                                }
                            }
                        }
                    }
                }
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
