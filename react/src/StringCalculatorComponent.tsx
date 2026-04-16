import React, { useState } from 'react'

function add(numbers: string): number {
  if (numbers === '') return 0

  let delimiter: RegExp = /,|\\n/
  let body = numbers

  if (numbers.startsWith('//')) {
    const separatorIndex = numbers.indexOf('\\n')
    const customDelimiter = numbers.slice(2, separatorIndex)
    delimiter = new RegExp(customDelimiter.replace(/[.*+?^${}()|[\]\\]/g, '\\$&'))
    body = numbers.slice(separatorIndex + 2)
  }

  const parsed = body.split(delimiter).map(n => parseInt(n, 10))

  const negatives = parsed.filter(n => n < 0)
  if (negatives.length > 0) {
    throw new Error(`negatives not allowed: ${negatives.join(', ')}`)
  }

  return parsed.filter(n => n <= 1000).reduce((sum, n) => sum + n, 0)
}

export function StringCalculatorComponent(): React.JSX.Element {
  const [input, setInput] = useState('')
  const [sum, setSum] = useState<string>('')

  const calculateSum = () => {
    try {
      setSum(String(add(input)))
    } catch (e) {
      setSum((e as Error).message)
    }
  }

  const onClick = async () => {
    await simulateComplexSlowOperation()
    calculateSum()
  }

  return (
    <div>
      <div>
        <input
          type="text"
          style={{ width: '400px' }}
          value={input}
          onChange={(e) => setInput(e.target.value)}
        />
        &nbsp;
        <button onClick={onClick}>Add</button>
      </div>
      <div>
        <label>Result:</label>
        &nbsp;
        <span data-testid="result">{sum}</span>
      </div>
    </div>
  )
}

async function simulateComplexSlowOperation(): Promise<void> {
  return new Promise((resolve) => setTimeout(resolve, 150))
}
