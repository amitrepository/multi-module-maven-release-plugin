package com.github.danielflower.mavenplugins.release;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.junit.Test;
import scaffolding.TestProject;

import java.io.IOException;

import static com.github.danielflower.mavenplugins.release.AnnotatedTagFinder.isPotentiallySameVersionIgnoringBuildNumber;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class AnnotatedTagTest {
    @Test
    public void gettersReturnValuesPassedIn() throws Exception {
        // yep, testing getters... but only because it isn't a simple POJO
        AnnotatedTag tag = AnnotatedTag.create("my-name", "the-version", "buildNumber");
        assertThat(tag.name(), equalTo("my-name"));
        assertThat(tag.version(), equalTo("the-version"));
        assertThat(tag.buildNumber(), equalTo("buildNumber"));
    }

    @Test
    public void aTagCanBeCreatedFromAGitTag() throws GitAPIException, IOException {
        TestProject project = TestProject.singleModuleProject();
        AnnotatedTag tag = AnnotatedTag.create("my-name", "the-version", "buildNumber");
        tag.saveAtHEAD(project.local);

        Ref ref = project.local.tagList().call().get(0);
        AnnotatedTag inflatedTag = AnnotatedTag.fromRef(project.local.getRepository(), ref);
        assertThat(inflatedTag.name(), equalTo("my-name"));
        assertThat(inflatedTag.version(), equalTo("the-version"));
        assertThat(inflatedTag.buildNumber(), equalTo("buildNumber"));
    }

}
