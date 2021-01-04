import React, { Component } from "react";
import {
    BrowserRouter as Router,
    Switch,
    Route
} from "react-router-dom";
import ProductFormView from "./Views/ProductFormView";
import ProductListView from "./Views/ProductListView";

export default class App extends Component {
    constructor() {
        super();
    }

    render() {
        return (
            <Router>
                <Switch>
                    <Route exact path={"/"} render={() => <ProductListView />}/>
                    <Route path={"/add"} render={() => <ProductFormView />}/>
                    <Route path={"/edit/:id"} render={() => <ProductFormView />} />
                </Switch>
            </Router>)
    }
}