import "./App.css";
import React from "react";
import Todo from "./Todo";
import { Paper, List, Container } from "@material-ui/core";
import AddTodo from "./AddTodo";

class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      item: [{ id: 0, title: "Todo 1", done: true }],
    };
  }

  // [+] 버튼 클릭 시 동작할 event handler 함수 구현
  add = item => {
    const thisItems = this.state.item; // 위에서 이미 작성한 두 개 Todo Items를 thisItems에 assign
    item.id = "ID-" + thisItems.length; // 현재 Todo Item 개수만큼을 ID 값으로
    item.done = false;
    thisItems.push(item); // 입력받은 Todo를 thisItems에 추가
    console.log("items: ", this.state.item);
  };

  delete = item => {
    const thisItems = this.state.item;
    const newItems = thisItems.filter(e => e.id !== item.id);
    this.setState({ item: newItems }, () => {
      console.log("Update Items: ", this.state.item);
    });
  };

  render() {
    var todoItems = this.state.item.length > 0 && (
      <Paper style={{ margin: 16 }}>
        <List>
          {this.state.item.map((item, idx) => (
            <Todo
              item={item}
              key={item.id}
              add={this.add}
              delete={this.delete}
            />
          ))}
        </List>
      </Paper>
    );

    // 위에서 작성한 add() 함수를 연결

    return (
      <div className="App">
        <Container maxWidth="md">
          <AddTodo add={this.add} />
          <div className="TodoList">{todoItems}</div>
        </Container>
      </div>
    );
  }
}

export default App;
