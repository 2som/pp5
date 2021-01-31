import React, { Component } from "react";
import {
    BrowserRouter as Router,
    Switch,
    Route
} from "react-router-dom";
import { Layout } from 'antd';

import AdminProductFormView from "./Views/AdminProductFormView";
import AdminProductListView from "./Views/AdminProductListView";
import ClientView from "./Views/ClientView";
import CustomSider from "./Components/CustomSider";

const { Header, Content, Footer } = Layout;

export default class App extends Component {
    constructor() {
        super();
    }

    render() {
        return (
        <Router>
            <Layout style={{ minHeight: '100vh' }}>
                <CustomSider />
                <Layout>
                    <Header style={{ padding: 0, backgroundColor: 'whitesmoke' }}/>
                    <Content>
                        <Switch>
                            <Route exact path={"/products"} render={() => <AdminProductListView />}/>
                            <Route path={"/products/add"} render={() => <AdminProductFormView />}/>
                            <Route path={"/products/edit/:id"} render={() => <AdminProductFormView />}/>
                            <Route path={"/"} component={ClientView}/>
                        </Switch>
                    </Content>
                    <Footer style={{ textAlign: 'center' }}/>
                </Layout>
            </Layout>
        </Router>
        )
    }
}