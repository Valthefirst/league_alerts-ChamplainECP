import { test, expect } from '@playwright/test';

test('test', async ({ page }) => {
  await page.goto('https://league-alerts.web.app/admin/createAuthor');
  await page.getByRole('link', { name: 'Create Account' }).click();
  const page1Promise = page.waitForEvent('popup');
  await page.getByRole('button', { name: 'Log In' }).click();
  const page1 = await page1Promise;
  await page1.getByLabel('Email address').click();
  await page1.getByLabel('Email address').fill('admin@admin.com');
  await page1.getByLabel('Email address').press('Tab');
  await page1.getByLabel('Password').fill('@AdminPassword123');
  await page1.getByRole('button', { name: 'Continue', exact: true }).click();
  await page.goto('https://league-alerts.web.app/admin/createAuthor');
  await page.getByPlaceholder('Enter your email').click();
  await page.getByPlaceholder('Enter your email').fill('@');
  await page.getByPlaceholder('Enter your first name').click();
  await page.getByPlaceholder('Enter your first name').fill('fdf');
  await page.getByPlaceholder('Enter your last name').click();
  await page.getByPlaceholder('Enter your last name').fill('a');
  await page.getByPlaceholder('Enter your password').click();
  await page.getByPlaceholder('Enter your password').fill('@');
  await page.getByRole('button', { name: 'Create Author' }).click();
  await expect(page.locator('#root')).toContainText('User created successfully! ID: undefined');
});