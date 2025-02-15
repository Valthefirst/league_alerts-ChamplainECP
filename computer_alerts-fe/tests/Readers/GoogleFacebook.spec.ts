import { test, expect } from "@playwright/test";

test("test", async ({ page }) => {
  await page.goto("http://localhost:3000/");
  await page.locator("#basic-navbar-nav").getByRole("img").click();
  await page.getByRole("link", { name: "Create Account / Sign In" }).click();
  const page1Promise = page.waitForEvent("popup");
  await page.getByRole("button", { name: "Log In" }).click();
  const page1 = await page1Promise;
  await expect(
    page1.getByRole("button", { name: "Continue with Google" }),
  ).toBeVisible();
  await expect(
    page1.getByRole("button", { name: "Continue with Google" }),
  ).toBeVisible();
});
