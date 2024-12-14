import { test, expect } from '@playwright/test';

test('Add Comment in ArticlePage', async ({ page }) => {
  await page.goto('http://localhost:3000/articles/ca1d0478-6a9c-421b-b815-84965e3c7b4a');
  await page.getByPlaceholder('Write a comment...').click();
  await page.getByPlaceholder('Write a comment...').fill('I was first ;)');
  await page.getByRole('button', { name: 'Add Comment' }).click();
  await expect(page.locator('#root')).toContainText('I was first ;)');

  await page.close();
});

test('Big Comment in ArticlePage', async ({ page }) => {
  await page.goto('http://localhost:3000/articles/ca1d0478-6a9c-421b-b815-84965e3c7b4a');
  await page.getByPlaceholder('Write a comment...').click();
  await page.getByPlaceholder('Write a comment...').fill('Apple mountain laptop freedom universe ' +
    'lighthouse keyboard ocean guitar willow cloud chocolate forest puzzle eagle rainbow sunset journey ' +
    'memory adventure snowflake volcano window dream notebook river camera compass heart horizon butterfly ' +
    'rocket whisper symphony puzzle treasure star galaxy secret flame oasis harmony meadow turtle dolphin ' +
    'book breeze lantern melody anchor mountain.');

    // Create dialog handler before click
  const dialogPromise = page.waitForEvent('dialog');
  page.once('dialog', dialog => {
    dialog.dismiss().catch(() => {});
  });
  await page.getByRole('button', { name: 'Add Comment' }).click();
  
  // Handle dialog and assert message
  const dialog = await dialogPromise;
  expect(dialog.message()).toBe('Comment is too long. Please keep it under 50 words.');
    
  await page.close();
});