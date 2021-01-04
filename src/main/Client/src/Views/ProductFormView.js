import React, { Component } from "react";
import { withRouter } from "react-router";
import { Row, Col, Card, Input, Button, message } from "antd";
import api from "../api";

class ProductFormView extends Component{
    constructor(){
        super(); 
        this.state = {
            description: "",
            price: 0,
            validatingProcess: false,
            operationType: "add"
        }
    }

    componentDidMount() {
        const { id } = this.props.match.params;
        if(id) {
            this.setState({ operationType: "edit" });
            this.fetchProductDetails(id);
        }
    }

    render() {
        const { id } = this.props.match.params;
        const { description, price, validatingProcess } = this.state;
        return(
            <Row justify="center">
                <Col span="6">
                    <Card title={"Add new product"}>
                        <Input.TextArea disabled={validatingProcess} rows={4} value={description} onChange={this.handleDescriptionChange} type={"text"} placeholder={"Description"}/>
                        <br />
                        <br />
                        <Input disabled={validatingProcess} type={"number"} value={price} onChange={this.handlePriceChange} placeholder={"price"}/>
                        <br />
                        <br />
                        <Row justify={"space-around"}>
                            <Col>
                                <Button onClick={this.handleSubmit}>Submit</Button>
                            </Col>
                            <Col>
                                <Button danger onClick={this.handleCancel}>Cancel</Button>
                            </Col>
                        </Row>
                    </Card>
                </Col>
            </Row>
        )
    }

    fetchProductDetails = (id) => {
        api
            .get(`products/${id}/`)
            .then(res => res.data)
            .then(product => this.setState({ price: product.price, description: product.description}))
            .catch(err => console.error(err))
    }

    handleDescriptionChange = (e) => {
        this.setState({ description: e.target.value });
    }

    handlePriceChange = (e) => {
        this.setState({ price: e.target.value });
    }

    handleSubmit = () => {
        this.setState({ validatingProcess: true });
        if(!this.validateInputs()){
            this.setState({ validatingProcess: false });
            return;
        }
        if(this.state.operationType === "add") {
            this.handlePostRequest();
        } else {
            this.handlePatchRequest();
        }
    }

    handleCancel = () => {
        this.resetState()
        this.props.history.push("/");
    }

    resetState = () => {
        this.setState({
            description: "",
            price: 0,
            validatingProcess: false
        });
    }

    validateInputs = () => {
        return this.validatePrice() && this.validateDescription();
    }

    validatePrice = () => {
        const { price } = this.state;
        if(price && price > 0) {
            return true;
        }
        message.warning("Price must be bigger than 0.");
    }

    validateDescription = () => {
        const { description } = this.state;
        if(description.trim().length > 0){
            return true;
        }
        
        message.warning("Description is required.");
        return false;
    }

    handlePostRequest = () => {
        api
            .post("/products/", {
                description: this.state.description,
                price: this.state.price,
                picture: null,
            })
            .then(() => this.props.history.push("/"))
            .catch(err => console.error(err))
            .finally(() => this.resetState());
    }

    handlePatchRequest = () => {
        const { id } = this.props.match.params;
        this.setState({ validatingProcess: true });
        
        api
            .patch(`/products/${id}/`, {
                description: this.state.description,
                price: this.state.price,
            })
            .then(() => this.props.history.push("/"))
            .then(() => message.success("Product updated."))
            .catch(err => console.error(err))
            .finally(() => this.setState({ validatingProcess: false }));
    }
}

export default withRouter(ProductFormView);