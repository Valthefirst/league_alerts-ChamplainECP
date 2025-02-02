import { test, expect } from "@playwright/test";

test("test", async ({ page }) => {
  await page.goto("http://localhost:3000/");
  await page.locator("#basic-navbar-nav").getByRole("img").click();
  await page.locator("li").filter({ hasText: "Modify Account" }).click();
  await page.getByRole("button", { name: "Update" }).click();
  await expect(page.getByText("You do not currently exist in")).toBeVisible();
  await expect(page.locator("#root")).toContainText(
    "You do not currently exist in our system, please contact someone or create new account",
  );
});
