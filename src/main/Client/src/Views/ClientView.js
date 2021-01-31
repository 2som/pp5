import React, { Component } from "react";
import { Table, Col, Row, Space, Card, Button, message } from "antd";
import {
  PlusOutlined,
  MinusOutlined
} from '@ant-design/icons';
import api from "../api";

export default class ClientView extends Component {
  constructor() {
    super();
    this.state = {
      products: [],
      basket: {
        total: 0,
        productsCount: 0,
        offerLines: []
      }
    }
  }

  componentDidMount() {
    this.fetchProducts();
    this.fetchBasket();
  }

  render() {
    const { basket, products } = this.state;
    return (
      <>
        <Row justify={"space-around"}>
          <Col>
            <Card title="Basket" style={{ width: 300 }}>
              {basket.offerLines.length > 0 && basket.offerLines.map(this.generateOffer)}
              <br />
              <Row justify={"space-between"}>
                <Col>
                  <p><strong>Total: {basket.total}</strong></p>
                </Col>
                <Col>
                  <p><strong>Products Count: {basket.productsCount}</strong></p>
                </Col>
              </Row>
              <Row>
                <Button onClick={() => message.success("Offer submited.")}>Submit Offer</Button>
              </Row>
            </Card>
          </Col>
            <Col span={16}>
              <Table dataSource={products} columns={this.productColumns}></Table>
            </Col>
        </Row>
      </>
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

  fetchBasket = () => {
    api
      .get("current-offer/")
      .then(res => res.data)
      .then(basket => this.setState({ basket: basket }))
      .catch(err => console.error(err));
  }

  addProductToBasket = (productId) => {
    api
      .post(`basket/add/${productId}`)
      .then(() => this.fetchBasket());
  }

  deleteProductFromBasket = (productId) => {
    api
      .post(`basket/delete/${productId}`)
      .then(() => this.fetchBasket());
  }

  generateOffer = (offer) => {
    return (
      <Row justify="space-around">
        <Col>Desc: {offer.description}</Col>
        <Col>Price: {offer.unitPrice}</Col>
        <Col><PlusOutlined onClick={() => this.addProductToBasket(offer.productId)} /> {offer.quantity} <MinusOutlined onClick={() => this.deleteProductFromBasket(offer.productId)} /></Col>
      </Row>
    )
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
                <a onClick={() => this.addProductToBasket(record.id)}>Add to basket</a>
            </Space>
        ),
    },
  ]
}