import { test, expect } from '@playwright/test';

test('test', async ({ page }) => {
  await page.goto('http://localhost:3000/');
  await page.locator('#basic-navbar-nav').getByRole('img').click();
  await page.getByRole('link', { name: 'Create Account / Sign In' }).click();
  const page1Promise = page.waitForEvent('popup');
  await page.getByRole('button', { name: 'Log In' }).click();
  const page1 = await page1Promise;
  await page1.getByRole('button', { name: 'Continue with Google' }).click();
  await expect(page1.locator('div').filter({ hasText: /^Forgot email\?$/ })).toBeVisible();
  await page1.getByLabel('Email or phone').click();
  await page1.goto('https://accounts.google.com/v3/signin/rejected?app_domain=https%3A%2F%2Flogin.ca.auth0.com&client_id=870216976608-q7pjgck3d643hi35crp0u49p3gtph36a.apps.googleusercontent.com&continue=https%3A%2F%2Faccounts.google.com%2Fsignin%2Foauth%2Fconsent%3Fauthuser%3Dunknown%26part%3DAJi8hAN_HJ5a1WgLzQwfYtDb-abiVNBeyszFv_jzJd3I9NzYkKfNMJfnxmkBhl22R3uKkvAz4xclr8FPJaKSTFLM8HX0uAj9xS44W2rGfmyi43E90r4Ce51zznQYfN9qtebuvUPwhkt2lBswblfWE0QXysppUb7j4wf3QsS7pIwKZ0KC1p_jkJrhH6xI2ZdycFlvEdC6WUCicOU5S0O8HvoW6bSsYGKRbt2O1g1CWAEESCEQyufFJBptifW_XrQdCYZso5E81FJunpuA3GHH6coPOgkC8lLEs2IX7CtlckAuCyQElt7qRKuUQyLVDOdPc4c5S1yIcAB6f7ewUmnQMWL68xI9SiTXUWdCDZ4fT39Pc2LCVGb3f84lLTLkkxG7yx4WseEx2aTJYSHIGlaFyj4E5s4ELkpKIYE8m7pj6NrTzPaYwHOIntYCD99WX3WP8NZa1-chQwL-gpWIt7Emuln3Z1PheuQFZg%26flowName%3DGeneralOAuthFlow%26as%3DS-1761191339%253A1739629631843668%26client_id%3D870216976608-q7pjgck3d643hi35crp0u49p3gtph36a.apps.googleusercontent.com%23&ddm=1&dsh=S-1761191339%3A1739629631843668&epd=AQd44mEO-APot5TKyRWZHSXmv_lEDcx0z4DOEIsceMmHiNEdHgXx8v8WAg&flowName=GeneralOAuthFlow&o2v=1&opparams=%253F&rart=ANgoxcf2O4nOyasWoZuNp7Fg1b9CPPMluDurEdFRlPpd45BeIngbQAWGNP4yv_LDDqkfv72HOzGAGxCBCN1aGndLMbbkaaX7HTu1XFJg_SvzByo7MLD1OQo&redirect_uri=https%3A%2F%2Flogin.ca.auth0.com%2Flogin%2Fcallback&response_type=code&rhlk=le&rrk=46&scope=email%20profile&service=lso&state=Fe26.2**e9ced7faa173504846f16d7ff8953c4eee3512a9b583ca06e9e209402326f177*he79g6Y0la1Q0Wi6D_pqhg*BPuJJIK1TMU9AVv59GTZqwzMZWNk1ceHw0nXflI5GnEyXbT0T81-4hOtJN5BVL2vAgJ4R3qQXHKpD2TP9q_L3lWP-hdZSgJ4RtzK6ie84N_ZzDjPp0YAkIRWQuG9oCjl**f5bebad82eb8da80e39514469a98fec9b4309f92901e729bec6155c533a70c74*rCvXYYB-mpxejUNGyVvk5itGWcpUY_MLGBnvgB9hmbo');
  await expect(page1.getByRole('heading', { name: 'Couldn’t sign you in' }).locator('span')).toBeVisible();
  await expect(page1.getByRole('heading', { name: 'Couldn’t sign you in' }).locator('span')).toBeVisible();
});