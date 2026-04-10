import React, { useState } from 'react'

export function StringCalculatorComponent(): React.JSX.Element {
  const [input, setInput] = useState('')
  const [result, setResult] = useState<string>('')

  const onClick = () => {
    if (input === '') setResult("0")
    else setResult(String(input.split(',').reduce((sum, n) => sum + parseInt(n, 10), 0)))
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
        <button onClick={onClick}>Calculate</button>
      </div>
      <div>
        <label>Result:</label>
        <span>{result}</span>
      </div>
    </div>
  )
}
