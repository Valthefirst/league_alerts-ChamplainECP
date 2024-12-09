import { test, expect } from '@playwright/test';

test('Author Article in AuthorDetails Page', async ({ page }) => {
  await page.goto('http://localhost:3000/articles/ca1d0478-6a9c-421b-b815-84965e3c7b4a');
  await page.getByRole('link', { name: 'George Smith' }).click();
  await expect(page.locator('#root')).toContainText('This is the body of article 3');
  
  await page.close();
});