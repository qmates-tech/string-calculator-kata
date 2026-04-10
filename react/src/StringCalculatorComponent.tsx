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

  return body.split(delimiter).reduce((sum, n) => sum + parseInt(n, 10), 0)
}

export function StringCalculatorComponent(): React.JSX.Element {
  const [input, setInput] = useState('')
  const [sum, setSum] = useState<string>('')

  const calculateSum = () => {
    setSum(String(add(input)))
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
  return new Promise((resolve) => setTimeout(resolve, 100))
}
