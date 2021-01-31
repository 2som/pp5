import React, { Component } from "react";
import {ExclamationCircleOutlined} from "@ant-design/icons";
import api from "../api";
import { Table, Space, Spin, Col, Row, Modal, message, Button } from "antd";
import { withRouter } from "react-router";

const { confirm } = Modal;

class AdminProductListView extends Component{
    constructor() {
        super();
        this.state = {
            products: [],
            loading: false,
        }
    }

    componentDidMount() {
        this.fetchProducts();
    }

    render() {
        return(
            <div>
                <Row justify={"center"}>
                    <Col span={10}>
                        <Spin spinning={this.state.loading} tip={"...Loading"}>
                            <Table dataSource={this.state.products} columns={this.productColumns}></Table>
                        </Spin>
                    </Col>
                </Row>
                <Row justify={"center"}>
                    <Col>
                        <Button onClick={this.handleAddProduct}>Add Product</Button>
                    </Col>
                </Row>
            </div>
        )
    }

    fetchProducts = () => {
        this.setState({ loading: true });
        api
            .get("products/")
            .then(res => res.data)
            .then(products => this.setState({ products: products }))
            .catch(err => console.error(err))
            .finally(() => this.setState({ loading: false }));
    }

    handleAddProduct = () => {
        this.props.history.push("/products/add");
    }

    showDeleteConfirm = (id, callback) => {
        confirm({
            title: 'Are you sure?',
            icon: <ExclamationCircleOutlined />,
            okText: 'Yes',
            okType: 'danger',
            cancelText: 'No',
            onOk: function() {
                callback(id)
            }
        });
    }

    handleDeleteProduct = (id) => {
        api
            .delete(`products/${id}/`)
            .then(() => this.setState(prevState => { return { products: prevState.products.filter(product => product.id !== id ) } }))
            .then(() => message.success("Product deleted."))
            .catch(err => console.error(err));
    }

    productColumns = [
        {
            title: 'Description',
            dataIndex: 'description',
            key: 'name',
        },
        {
            title: 'Price',
            dataIndex: 'price',
            key: 'price',
        },
        {
            title: 'Action',
            key: 'action',
            render: (record) => (
                <Space size="middle">
                    <a onClick={() => this.props.history.push(`/products/edit/${record.id}`)}>Edit</a>
                    <a onClick={() => this.showDeleteConfirm(record.id, this.handleDeleteProduct)}>Delete</a>
                </Space>
            ),
        },
    ]
}

export default withRouter(AdminProductListView);