import { test, expect } from '@playwright/test';

test('test', async ({ page }) => {
  await page.goto('https://league-alerts.web.app/admin/createAuthor');
  await page.getByPlaceholder('Enter your email').click();
  await page.getByPlaceholder('Enter your email').fill('@');
  await page.getByPlaceholder('Enter your first name').click();
  await page.getByPlaceholder('Enter your first name').fill('r');
  await page.getByPlaceholder('Enter your last name').click();
  await page.getByPlaceholder('Enter your password').click();
  await page.getByPlaceholder('Enter your password').fill('A');
  await page.getByRole('button', { name: 'Create Author' }).click();
  await expect(page.locator('#root')).toContainText('Failed to execute \'json\' on \'Response\': Unexpected end of JSON input');
});