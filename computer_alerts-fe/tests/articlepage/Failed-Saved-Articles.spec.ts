import { test, expect } from '@playwright/test';



test('test', async ({ page }) => {
  await page.goto('https://league-alerts.web.app/');
  await page.getByRole('link', { name: 'NBA' }).click();
  await page.getByRole('link', { name: 'Create Account' }).click();
  await expect(page.locator('#root')).toContainText('Log In');
  await page.getByRole('link', { name: 'Home' }).click();
  await page.getByRole('link', { name: 'NBA' }).nth(1).click();
  await page.getByRole('button', { name: 'Save' }).first().click();
  await page.getByRole('button', { name: 'Save' }).first().click();
  await page.getByText('The NBA is Back on Jan 29!Posted: January 15, 2025 at 04:38 PM0SaveNBA').click({
    button: 'right'
  });
  await page.getByRole('heading', { name: 'The NBA is Back on Jan 29!' }).click();
  await page.getByRole('link', { name: 'Saved Articles' }).click();
  await expect(page.locator('#root')).toContainText('Failed to fetch saves');
});