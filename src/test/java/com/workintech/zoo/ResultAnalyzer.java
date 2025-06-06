package com.workintech.zoo;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.util.Optional;

public class ResultAnalyzer implements TestWatcher, AfterAllCallback {

    private int testsSucceeded = 0;
    private int testsFailed = 0;
    private int testsSkipped = 0;

    @Override
    public void testSuccessful(ExtensionContext context) {
        testsSucceeded++;
        System.out.println("✅ Test passed: " + context.getDisplayName());
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        testsFailed++;
        System.out.println("❌ Test failed: " + context.getDisplayName() + " - " + cause.getMessage());
    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        testsSkipped++;
        String reasonText = reason.orElse("No reason provided");
        System.out.println("⏭️ Test skipped: " + context.getDisplayName() + " - " + reasonText);
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        System.out.println("\n=== Test Results ===");
        System.out.println("✅ Passed: " + testsSucceeded);
        System.out.println("❌ Failed: " + testsFailed);
        System.out.println("⏭️ Skipped: " + testsSkipped);
        System.out.println("Total: " + (testsSucceeded + testsFailed + testsSkipped));
    }
}
