import 'jest-extended';
import { StringCalculator } from '../src/string_calculator';

const stringCalculator = new StringCalculator()

test('returns zero with empty string', () => {
  const actual = stringCalculator.sum("")
  expect(actual).toBe(0)
})

test('single digit string', () => {
  const actual = stringCalculator.sum("7")
  expect(actual).toBe(7)
})
