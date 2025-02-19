import { test, expect } from "@playwright/test";

test("Order ascending saved articles", async ({ page }) => {
  await page.goto("http://localhost:3000/home");
  await page.getByRole("link", { name: "Create Account" }).click();
  const page1Promise = page.waitForEvent("popup");
  await page.getByRole("button", { name: "Log In" }).click();
  const page1 = await page1Promise;
  await page1.getByLabel("Email address").click();
  await page1.getByLabel("Email address").fill("Reader1@gmail.com");
  await page1.getByLabel("Email address").press("Tab");
  await page1.getByLabel("Password").fill("@Reader123");
  await page1.getByRole("button", { name: "Continue", exact: true }).click();
  await page.getByRole('link', { name: 'Saved Articles' }).click();
  await expect(page.getByRole('button')).toContainText('Sort by Date: Ascending');
  await expect(page.locator('#root')).toMatchAriaSnapshot(`- heading "MLB Season Update" [level=1]`);
  
});

test("Order descending saved articles", async ({ page }) => {
    await page.goto("http://localhost:3000/home");
    await page.getByRole("link", { name: "Create Account" }).click();
    const page1Promise = page.waitForEvent("popup");
    await page.getByRole("button", { name: "Log In" }).click();
    const page1 = await page1Promise;
    await page1.getByLabel("Email address").click();
    await page1.getByLabel("Email address").fill("Reader1@gmail.com");
    await page1.getByLabel("Email address").press("Tab");
    await page1.getByLabel("Password").fill("@Reader123");
    await page1.getByRole("button", { name: "Continue", exact: true }).click();
    await page.getByRole('link', { name: 'Saved Articles' }).click();
    await page.getByRole('button', { name: 'Sort by Date: Ascending' }).click();
    await expect(page.getByRole('button')).toContainText('Sort by Date: Descending');
    await expect(page.locator('#root')).toMatchAriaSnapshot(`- heading "MLB Playoff" [level=1]`);
  });
