import { test, expect } from "@playwright/test";

test("test", async ({ page }) => {
  await page.goto("http://localhost:3000/");
  await page.getByRole("link", { name: "NBA" }).click();
  await page.getByRole("img", { name: "The NBA is Back!" }).click();
  await page.getByRole("link", { name: "NBA" }).click();
  await page.getByRole("img", { name: "The NBA is Back!" }).click();
  await page.getByRole("link", { name: "NBA" }).click();
  await page.getByRole("img", { name: "The NBA is Back!" }).click();
  await page.getByRole("link", { name: "NBA" }).click();
  await page.getByRole("img", { name: "The NBA is Back!" }).click();
  await page.getByRole("link", { name: "NBA" }).click();
  await page.getByRole("link", { name: "Home" }).click();
  await expect(
    page.getByRole("heading", { name: "The NBA is Back!" }),
  ).toBeVisible();
  await page.getByRole("img", { name: "The NBA is Back!" }).click();
});
