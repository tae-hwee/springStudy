import "./App.css";
import React from "react";
import Todo from "./Todo";

class App extends React.Component {
  render() {
    return (
      <div className="App">
        <Todo />
        <Todo />
      </div>
    );
  }
}

export default App;
