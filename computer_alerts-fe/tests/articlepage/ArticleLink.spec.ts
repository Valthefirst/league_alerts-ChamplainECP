import { test, expect } from '@playwright/test';

test('test', async ({ page }) => {
  await page.goto('https://league-alerts.web.app/articles/e09e8812-32fb-434d-908f-40d5e3b137ca');
  await page.getByRole('img', { name: 'Share' }).click();
  await expect(page.getByText('https://league-alerts.web.app')).toBeVisible();
  await expect(page.locator('h1')).toMatchAriaSnapshot(`- heading /The NBA is Back on Jan \\d+!/ [level=1]`);
});