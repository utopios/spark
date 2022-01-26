const { Kafka } = require("kafkajs")

// the client ID lets kafka know who's producing the messages
const clientId = "my-app"
// we can define the list of brokers in the cluster
const brokers = ["localhost:9092"]
// this is the topic to which we want to write messages
const topic = "interact"

const data = [
    {customerId : 1, firstName: "jean", lastName: "dupond"},
    {customerId : 2, firstName: "will", lastName: "Jean-Luc"},
    {customerId : 1, firstName: "jean", lastName: "dupond"},
    {customerId : 2, firstName: "will", lastName: "Jean-Luc"},
    {customerId : null, firstName: "Jadzia", lastName: "Jean"},

]
// initialize a new kafka client and initialize a producer from it
const kafka = new Kafka({ clientId, brokers })
const producer = kafka.producer()

// we define an async function that writes a new message each second
const produce = async () => {
    await producer.connect()
    let i = 0

    // after the produce has connected, we start an interval timer
    setInterval(async () => {
        try {
            // send a message to the configured topic with
            // the key and value formed from the current value of `i`
            await producer.send({
                topic,
                messages: [
                    {
                        key: i.toString(),
                        value: JSON.stringify(data[i])
                    }
                ],
            })

            // if the message is written successfully, log it and increment `i`
            console.log("writes: ", i)
            i++
        } catch (err) {
            console.error("could not write message " + err)
        }
    }, 5000)
}
produce().then((res) => {
    console.log(res)
})