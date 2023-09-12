import 'jest-extended';
import { StringCalculator } from '../src/string_calculator';

test('returns zero with empty string', () => {
  const stringCalculator = new StringCalculator()

  const actual = stringCalculator.sum("")

  expect(actual).toBe(0)
})
