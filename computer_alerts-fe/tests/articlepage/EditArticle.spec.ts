import { test, expect } from '@playwright/test';

test('test', async ({ page }) => {
  await page.goto('https://league-alerts.web.app/create-account');
  const page1Promise = page.waitForEvent('popup');
  await page.getByRole('button', { name: 'Log In' }).click();
  const page1 = await page1Promise;
  await page1.getByLabel('Email address').click();
  await page1.getByLabel('Email address').fill('Author1@gmail.com');
  await page1.getByLabel('Email address').press('Tab');
  await page1.getByLabel('Password').fill('@AuthorPassword123');
  await page1.getByRole('button', { name: 'Continue', exact: true }).click();
  await page.getByRole('link', { name: 'NHL' }).click();
  await page.getByRole('heading', { name: 'NHL Season Update' }).click();
  await page.getByRole('button', { name: 'Edit Article' }).click();
  await page.locator('input[name="title"]').click();
  await page.locator('input[name="title"]').fill('NHL Season Uppdate');
  await page.getByRole('button', { name: 'Update Article' }).click();
  await page.goto('https://league-alerts.web.app/articles/ca1d0478-6a9c-421b-b815-84965e3c7b4a');
  await expect(page.locator('h1')).toContainText('NHL Season Uppdate');
});