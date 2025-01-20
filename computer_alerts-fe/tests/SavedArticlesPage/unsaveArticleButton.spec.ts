import { test, expect } from '@playwright/test';

test('Assert the Unsave button is there', async ({ page }) => {
  await page.goto('http://localhost:3000/saved-articles');
  await expect(page.locator('#root')).toMatchAriaSnapshot(`- button " Unsave"`);
  await expect(page.locator('.btn').first()).toBeVisible();
  
  await page.close();
});