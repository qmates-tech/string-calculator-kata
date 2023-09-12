export class StringCalculator {
  sum(inputString: string): number {
    if(inputString === "")
      return 0

    return parseInt(inputString)
  }
}