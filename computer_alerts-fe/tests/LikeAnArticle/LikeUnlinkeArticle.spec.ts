import { test, expect } from '@playwright/test';

test('test', async ({ page }) => {
  await page.goto('http://localhost:3000/');
  await page.getByRole('link', { name: 'Home' }).click();
  await page.locator('#heart').first().click();
  await expect(page.getByText('1')).toBeVisible();
  await page.locator('div').filter({ hasText: /^1$/ }).locator('#heart').click();
  await expect(page.getByText('0').first()).toBeVisible();
});