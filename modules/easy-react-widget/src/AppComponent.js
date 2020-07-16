import React, { useState } from "react";

const AppComponent = () => {
  const [exampleState, setExampleState] = useState({
    value1: "",
    value2: "",
  });

  const onSubmit = (event) => {
    alert("value1: " + exampleState.value1 + " value2: " + exampleState.value2);
  };
  const onChange = (event) => {
    const { name, value } = event.target;
    console.log(name, value);
    setExampleState({
      ...exampleState,
      [name]: value,
    });
    console.log(exampleState);
  };

  return (
    <div className="App">
      {Liferay.Language.get("this.should.be.something.in.liferay.instance")}
      <form onSubmit={onSubmit}>
        <input
          className="input"
          type="text"
          name="value1"
          id="value1"
          onChange={(event) => {
            onChange(event);
          }}
        />
        <label className="form-check-label" htmlFor="value1">
          input1
        </label>
        <input
          className="input"
          type="text"
          name="value2"
          id="value2"
          onChange={(event) => {
            onChange(event);
          }}
        />
        <label className="form-check-label" htmlFor="value2">
          input1
        </label>

        <button role="submit">send</button>
      </form>
    </div>
  );
};

export default AppComponent;