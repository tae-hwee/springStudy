import React from "react";
import { TextField, Paper, Button, Grid } from "@material-ui/core";

class AddTodo extends React.Component {
  constructor(props) {
    super(props);
    this.state = { item: { title: "" } }; // 사용자의 입력을 저장할 object
  }

  // text field에 사용자의 입력을 받을 때마다 onChange를 props로 받는 handler 함수인 onInputChange() 구현
  onInputChange = e => {
    const thisItem = this.state.item;
    thisItem.title = e.target.value;
    this.setState({ item: thisItem });
    console.log(thisItem);
  };

  render() {
    // 구현한 onInputChange() 함수를 연결
    return (
      <Paper style={{ margin: 16, padding: 16 }}>
        <Grid container>
          <Grid xs={11} md={11} item style={{ paddingRight: 16 }}>
            <TextField
              placeholder="Add Todo here"
              fullWidth
              onChange={this.onInputChange}
              value={this.state.item.title}
            />
          </Grid>
          <Grid xs={1} md={1} item>
            <Button fullWidth color="secondary" variant="outlined">
              +
            </Button>
          </Grid>
        </Grid>
      </Paper>
    );
  }
}

export default AddTodo;
