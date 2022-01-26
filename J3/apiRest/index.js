const express = require("express")
const cors = require("cors")
const parser = require("body-parser")
const app = express()

app.use(cors())
app.use(parser.json())

const dataCustomer = [
    {customerId : 3, firstName: "Jadzia", lastName: "Jean"},
]

const dataSend = []
app.post("/getcustomer", (req, res) => {
    const data =req.body
    const response = dataCustomer.find(p => p.firstName == data.firstName && p.lastName == data.lastName)
    res.json(response)
})

app.post("/data", (req, res) => {
    const data =req.body
    dataSend.push(data)
    res.json({message : "Ok"})
})

app.get("/data", (req, res) => {
    res.json(dataSend)
})

app.listen(3000, () => {

})