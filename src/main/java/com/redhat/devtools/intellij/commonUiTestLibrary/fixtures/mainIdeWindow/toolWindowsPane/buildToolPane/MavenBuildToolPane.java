/*******************************************************************************
 * Copyright (c) 2020 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 * Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package com.redhat.devtools.intellij.commonUiTestLibrary.fixtures.mainIdeWindow.toolWindowsPane.buildToolPane;

import com.intellij.remoterobot.RemoteRobot;
import com.intellij.remoterobot.data.RemoteComponent;
import com.intellij.remoterobot.fixtures.CommonContainerFixture;
import com.intellij.remoterobot.fixtures.DefaultXpath;
import com.intellij.remoterobot.fixtures.FixtureName;
import com.intellij.remoterobot.fixtures.JTreeFixture;
import com.intellij.remoterobot.fixtures.dataExtractor.RemoteText;
import com.redhat.devtools.intellij.commonUiTestLibrary.fixtures.mainIdeWindow.toolWindowsPane.BuildView;
import com.redhat.devtools.intellij.commonUiTestLibrary.fixtures.mainIdeWindow.toolWindowsPane.ToolWindowsPane;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

import static com.intellij.remoterobot.search.locators.Locators.byXpath;
import static com.intellij.remoterobot.utils.RepeatUtilsKt.waitFor;
import static com.redhat.devtools.intellij.commonUiTestLibrary.utils.textTranformation.TextUtils.listOfRemoteTextToString;

/**
 * Maven Build Tool Pane fixture
 *
 * @author zcervink@redhat.com
 */
@DefaultXpath(by = "ToolWindowsPane type", xpath = "//div[@accessiblename='Maven Tool Window']")
@FixtureName(name = "Tool Windows Pane")
public class MavenBuildToolPane extends CommonContainerFixture {
    private RemoteRobot remoteRobot;

    public MavenBuildToolPane(@NotNull RemoteRobot remoteRobot, @NotNull RemoteComponent remoteComponent) {
        super(remoteRobot, remoteComponent);
        this.remoteRobot = remoteRobot;
    }

    /**
     * Reload all Maven projects
     */
    public void reloadAllMavenProjects() {
        actionButton(byXpath("//div[@myicon='refresh.svg']"), Duration.ofSeconds(2)).click();
    }

    /**
     * Collapse all
     */
    public void collapseAll() {
        actionButton(byXpath("//div[contains(@myvisibleactions, 'For')]//div[@myicon='collapseall.svg']"), Duration.ofSeconds(2)).click();
    }

    /**
     * Build the project
     */
    public void buildProject() {
        waitFor(Duration.ofSeconds(30), Duration.ofSeconds(2), "The Maven target tree did not appear in 30 seconds.", () -> isMavenTreeVisible());
        mavenTargetTree().expandAll();
        mavenTargetTree().findAllText("install").get(0).doubleClick();
        remoteRobot.find(ToolWindowsPane.class).find(BuildView.class).waitUntilBuildHasFinished();
    }

    private void expandMavenTargetTreeIfNecessary() {
        try {
            mavenTargetTree().findText("Lifecycle");
        } catch (NoSuchElementException e) {
            List<RemoteText> mavenBuildLabels = mavenTargetTree().findAllText();
            Collections.reverse(mavenBuildLabels);
            for (RemoteText label : mavenBuildLabels) {
                label.doubleClick();
            }
        }
    }

    private boolean isMavenTreeVisible() {
        String treeContent = listOfRemoteTextToString(mavenTargetTree().findAllText());
        return !treeContent.toLowerCase(Locale.ROOT).contains("nothing") && !treeContent.equals("");
    }

    private JTreeFixture mavenTargetTree() {
        return find(JTreeFixture.class, JTreeFixture.Companion.byType(), Duration.ofSeconds(10));
    }
}