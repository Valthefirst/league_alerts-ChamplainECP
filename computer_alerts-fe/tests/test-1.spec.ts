import { test, expect } from '@playwright/test';

test('test', async ({ page }) => {
  await page.goto('https://league-alerts.web.app/create-account');
  await page.getByRole('link', { name: 'NHL' }).click();
  await page.getByRole('heading', { name: 'What is Happening in the NHL?' }).click();
  await page.getByRole('button', { name: 'Edit Article' }).click();
  await page.locator('input[name="title"]').click();
  await page.locator('input[name="title"]').press('ControlOrMeta+ArrowRight');
  await page.locator('input[name="title"]').press('ControlOrMeta+ArrowRight');
  await page.locator('input[name="title"]').press('ControlOrMeta+ArrowRight');
  await page.locator('input[name="title"]').press('ControlOrMeta+ArrowRight');
  await page.locator('input[name="title"]').press('ControlOrMeta+ArrowRight');
  await page.locator('input[name="title"]').fill('What is Happening in the NHL');
  await page.locator('input[name="title"]').click();
  await page.locator('input[name="title"]').click();
  await page.getByRole('button', { name: 'Update Article' }).click();
  await page.locator('input[name="title"]').click();
  await page.locator('input[name="title"]').fill('s');
  await page.getByRole('button', { name: 'Update Article' }).click();
});