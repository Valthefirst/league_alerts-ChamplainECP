import { test, expect } from "@playwright/test";

test("test", async ({ page }) => {
  await page.goto("http://localhost:3000/authCreateArticle");
  await page.getByPlaceholder("Title").click();
  await page.getByPlaceholder("Title").fill("w");
  await page.getByPlaceholder("Body").click();
  await page.getByPlaceholder("Body").fill("q");
  await page.getByRole("combobox").selectOption("UFC");
  await page.getByRole("button", { name: "Draft Article" }).click();
  await page.getByRole("button", { name: "Accept" }).click();
  await page.getByRole("link", { name: "Draft Articles" }).click();
  await expect(
    page
      .locator("div")
      .filter({ hasText: /^wqwqwqWord Count: 4Tags: UFC$/ })
      .nth(1),
  ).toBeVisible();
});
