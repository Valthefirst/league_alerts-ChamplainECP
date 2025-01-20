import { test, expect } from '@playwright/test';

test('Save an article', async ({ page }) => {
  await page.goto('https://league-alerts.web.app/home');
  await page.getByRole('link', { name: 'Create Account' }).click();
  const page1Promise = page.waitForEvent('popup');
  await page.getByRole('button', { name: 'Log In' }).click();
  const page1 = await page1Promise;
  await page1.getByLabel('Email address').click();
  await page1.getByLabel('Email address').fill('Reader1@gmail.com');
  await page1.getByLabel('Email address').press('Tab');
  await page1.getByLabel('Password').fill('@Reader123');
  await page1.getByRole('button', { name: 'Continue', exact: true }).click();
  await page.getByRole('link', { name: 'NHL' }).click();
  await page.getByRole('button', { name: 'Save', exact: true }).nth(0).click();
  await expect(page.locator('#root')).toContainText('Unsave');
});