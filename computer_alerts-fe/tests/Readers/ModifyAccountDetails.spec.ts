import { test, expect } from "@playwright/test";

test("test", async ({ page }) => {
  await page.goto("http://localhost:3000/");
  await page.locator("#basic-navbar-nav").getByRole("img").click();
  await page.getByRole("link", { name: "Create Account" }).click();
  const page1Promise = page.waitForEvent("popup");
  await page.getByRole("button", { name: "Log In" }).click();
  const page1 = await page1Promise;
  await page1.getByLabel("Email address").click();
  await page1.getByLabel("Email address").fill("reader2@gmail.com");
  await page1.getByLabel("Email address").press("Tab");
  await page1.getByLabel("Password").fill("@Reader123");
  await page1.getByRole("button", { name: "Show password" }).click();
  await page1.getByRole("button", { name: "Continue", exact: true }).click();
  await page
    .locator("#webpack-dev-server-client-overlay")
    .contentFrame()
    .getByLabel("Dismiss")
    .click();
  await page.locator("#basic-navbar-nav").getByRole("img").click();
  await page.getByRole("link", { name: "Modify Account" }).click();
  await page.getByLabel("First Name").click();
  await page.getByLabel("First Name").fill("Ja");
  await page.getByRole("button", { name: "Update" }).click();
  await expect(page.getByText("You account has been")).toBeVisible();
  await expect(page.locator("#root")).toContainText(
    "You account has been successfully updated !",
  );
});
