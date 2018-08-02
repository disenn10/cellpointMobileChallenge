package com.example.disen.cellpointassessment.utils;

import java.util.Comparator;

/**
 * Created by disen on 7/23/2018.
 */

public class GitUser {
    String name;
    String url;
    String description;
    String created;
    String updated;
    int watchers;
    String language;
    int noLanguages;

    public GitUser(String language, int noLanguages){
        this.language = language;
        this.noLanguages = noLanguages;
    }

    public GitUser(String name, String url, String description, String created, String updated, int watchers){
        this.name = name;
        this.url = url;
        this.description = description;
        this.created = created;
        this.updated = updated;
        this.watchers = watchers;
    }

    public int getNoLanguages() {
        return noLanguages;
    }

    public String getLanguage() {
        return language;
    }

    public int getWatchers() {
        return watchers;
    }

    public String getCreated() {
        return created;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getUpdated() {
        return updated;
    }

    public String getUrl() {
        return url;
    }

    public static Comparator<GitUser> SectionListComparator = new Comparator<GitUser>() {

        @Override
        public int compare(GitUser s1, GitUser s2) {
            int firstSec = s1.getNoLanguages();
            int secondSec = s2.getNoLanguages();
            return secondSec - firstSec;
        }

    };
}
