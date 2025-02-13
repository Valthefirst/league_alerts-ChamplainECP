import { test, expect } from '@playwright/test';

test('test', async ({ page }) => {
  await page.goto('https://league-alerts.web.app/home');
  await page.getByRole('img', { name: 'Subscribe to Notifications' }).click();
  await page.getByPlaceholder('Enter your email').click();
  await page.getByPlaceholder('Enter your email').fill('j');
  await page.getByRole('button', { name: 'Subscribe' }).click();
  await expect(page.getByText('Successfully subscribed to NBA')).toBeVisible();
  await expect(page.locator('#root')).toContainText('Successfully subscribed to NBA');
});