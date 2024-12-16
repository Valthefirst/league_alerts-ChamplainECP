import { test, expect } from '@playwright/test';

test('test', async ({ page }) => {
  await page.goto('http://localhost:3000/authCreateArticle');
  await page.getByPlaceholder('Title').click();
  await page.getByPlaceholder('Title').fill('H');
  await page.getByPlaceholder('Body').click();
  await page.getByPlaceholder('Body').fill('he');
  await page.getByRole('combobox').selectOption('NHL');
  await page.getByRole('button', { name: 'Create Article' }).click();
  await page.getByRole('button', { name: 'Accept' }).click();
  await page.goto('http://localhost:3000/adminHomePage');
  await page.getByRole('link', { name: 'Review Pending Articles' }).click();
  await expect(page.locator('div').filter({ hasText: /^HelloWord Count: 1Tags: NHL$/ }).nth(1)).toBeVisible();('http://localhost:3000/authCreateArticle');('http://localhost:3000/authCreateArticle');
  await expect(page.getByPlaceholder('Title')).toBeVisible();
  await page.getByPlaceholder('Title').dblclick();
  await page.getByPlaceholder('Title').fill('s');
});