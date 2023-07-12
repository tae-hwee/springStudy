import "./App.css";
import React from "react";
import Todo from "./Todo";

class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      item: { id: 0, title: "Hello World 1", done: false },
    };
  }

  render() {
    return (
      <div className="App">
        <Todo item={this.state.item} />
      </div>
    );
  }
}

export default App;
