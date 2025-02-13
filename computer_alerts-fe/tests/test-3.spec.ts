import { test, expect } from '@playwright/test';

test('test', async ({ page }) => {
  await page.goto('https://league-alerts.web.app/adminreports');
  await expect(page.getByText('Loading...')).toBeVisible();
});