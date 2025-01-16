import { test, expect } from "@playwright/test";

test("test", async ({ page }) => {
  await page.goto("http://localhost:3000/authCreateArticle");
  await page.getByPlaceholder("Title").click();
  await page.getByPlaceholder("Title").fill("H");
  await page.getByPlaceholder("Body").click();
  await page.getByPlaceholder("Body").fill("JEn jeknfkje wenkjjw e");
  await page.getByRole("combobox").selectOption("UFC");
  await page.getByRole("button", { name: "Create Article" }).click();
  await page.getByRole("button", { name: "Accept" }).click();
  await page.goto("http://localhost:3000/adminHomePage");
  await page.getByRole("link", { name: "Review Pending Articles" }).click();
  await page
    .locator("div")
    .filter({ hasText: /^HelloWord Count: 4Tags: UFC$/ })
    .first()
    .click();
  await page.getByRole("button", { name: "Accept Article" }).click();
  await page.getByRole("link", { name: "UFC" }).click();
  await expect(page.getByRole("img", { name: "Hello" })).toBeVisible();
});
