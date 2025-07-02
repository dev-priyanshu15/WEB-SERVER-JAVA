# Java Web Server Implementation

A comprehensive Java web server implementation demonstrating various server architectures and concurrent programming concepts.

## 📁 Project Structure

```
WEB-SERVER-JAVA/
├── .git/
├── .qodo/
├── Fileread/
│   ├── Client.class
│   ├── Client.java
│   ├── Clients$1.class
│   ├── data.txt
│   └── Server.class
│   └── Server.java
├── Multithread/
│   ├── Client.class
│   ├── Client.java
│   ├── Clients$1.class
│   ├── Server.class
│   └── Server.java
├── SingleThread/
│   ├── Client.class
│   ├── Client.java
│   ├── Server.class
│   └── Server.java
└── ThreadPool/
    ├── Server.class
    └── Server.java
```

## 🚀 Features

This project implements four different web server architectures:

### 1. **Single-Threaded Server** (`SingleThread/`)
- **Sequential processing**: Handles one client at a time
- **Port**: 8010 (consistent across all implementations)
- **Timeout**: 10-second socket timeout
- **Simple architecture**: Basic socket programming demonstration
- **Blocking I/O**: Next client waits until current client is fully processed

### 2. **Multi-Threaded Server** (`Multithread/`)
- **Dynamic threading**: Creates new thread for each client connection
- **Consumer-based architecture**: Uses functional programming with Lambda expressions
- **Port**: 8010 (same as File Reading Server)
- **Timeout**: 10-second socket timeout
- **Client testing**: Includes 100 concurrent client simulation

### 3. **Thread Pool Server** (`ThreadPool/`)
- **Fixed thread pool**: Uses exactly 10 threads for all client connections
- **Port**: 8010 (consistent with other implementations)
- **Timeout**: 70-second socket timeout (longest of all servers)
- **Resource efficient**: Reuses threads instead of creating new ones
- **Personalized response**: Includes client IP address in response

### 4. **File Reading Server** (`Fileread/`)
- **Hybrid approach**: Combines thread pool with file serving
- Pre-loads file content into memory for optimal performance
- Serves `data.txt` content to multiple clients simultaneously
- Uses fixed thread pool (100 threads) for concurrent client handling
- Runs on port **8010**

## 🛠️ Technologies Used

- **Java**: Core programming language
- **Socket Programming**: For network communication
- **Multithreading**: For concurrent client handling
- **Thread Pools**: For optimized resource management
- **File I/O**: For serving static content

## 📋 Prerequisites

- Java Development Kit (JDK) 8 or higher
- Basic understanding of networking concepts
- Command line interface

## 🏃‍♂️ Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/dev-priyanshu15/WEB-SERVER-JAVA.git
cd WEB-SERVER-JAVA
```

### 2. Compile and Run

Choose the server implementation you want to test:

#### Single-Threaded Server
```bash
cd SingleThread
javac Server.java Client.java
java Server
# Server starts on port 8010 (sequential client handling)
# In another terminal
java Client
# Note: Only one client can connect at a time!
```

**Server Features:**
- **Port**: 8010
- **Architecture**: Simple blocking I/O
- **Threading**: None - sequential processing
- **Timeout**: 10 seconds
- **Message**: Sends "Hello from the server"

#### Multi-Threaded Server
```bash
cd Multithread
javac Server.java Client.java
java Server
# Server starts on port 8010 with 10-second timeout
# In another terminal - runs 100 concurrent clients!
java Client
```

**Server Features:**
- **Port**: 8010
- **Architecture**: Consumer<Socket> with Lambda expressions
- **Threading**: Unlimited threads (one per client)
- **Timeout**: 10 seconds
- **Message**: Sends "Hello from the server" to each client

#### Thread Pool Server
```bash
cd ThreadPool
javac Server.java
java Server
# Server starts with 10-thread pool on port 8010
# Use any client from other implementations to test
cd ../SingleThread  # or Multithread or Fileread
java Client
```

**Server Features:**
- **Port**: 8010
- **Thread Pool**: Fixed 10 threads (configurable)
- **Timeout**: 70 seconds (longest timeout)
- **Message**: "Hello from server [Client-IP-Address]"
- **Resource Management**: Threads are reused efficiently

#### File Reading Server (Advanced)
```bash
cd Fileread
# Make sure data.txt exists in the directory
echo "Hello from server!" > data.txt
echo "This is line 2" >> data.txt
echo "File serving example" >> data.txt

javac Server.java Client.java
java Server
# Server will start on port 8010
# In another terminal
java Client
```

**Server Features:**
- **Port**: 8010
- **Thread Pool**: 100 concurrent connections
- **File**: Serves `data.txt` content
- **Performance**: Pre-loads file into memory

## 🔧 Configuration

### Default Settings
- **All Servers**: Port 8010, localhost
- **Single-Thread**: 10-second timeout, sequential processing
- **Multi-Thread**: 10-second timeout, unlimited threads
- **Thread Pool**: 70-second timeout, 10 fixed threads
- **File Reading**: No explicit timeout, 100 fixed threads

### Customization
You can modify the following parameters in the respective server files:
- Port number
- Thread pool size
- Buffer sizes
- Timeout values

## 📊 Performance Comparison

| Implementation | Concurrency | Resource Usage | Port | Threads | Timeout | Special Features |
|---------------|-------------|----------------|------|---------|---------|------------------|
| Single-Thread | None (Sequential) | Very Low | 8010 | 1 (main) | 10s | Basic socket programming |
| Multi-Thread | Unlimited | High | 8010 | Unlimited | 10s | Lambda expressions, Consumer pattern |
| Thread Pool | High (Limited) | Medium | 8010 | 10 (fixed) | 70s | Resource management, IP logging |
| File Reading | High (Limited) | Medium-High | 8010 | 100 (fixed) | None | Pre-loaded file serving |

## 🧪 Testing

### Single-Threaded Server Testing
```bash
# Start server
cd SingleThread
java Server
# Output: "Server is listening on port 8010"

# Test with one client
java Client
# Output: "📥 Response from the server is: Hello from the server"

# Try concurrent clients (second will wait!)
java Client &  # First client
java Client    # Second client waits until first completes
```

### Multi-Threaded Server Testing
```bash
# Start server
cd Multithread
java Server
# Output: "✅ Server is listening on port 8010"

# Run 100 concurrent clients (automatic load test!)
java Client
# This creates 100 simultaneous connections to test server performance
```

### Thread Pool Server Testing
```bash
# Start server (10 threads max)
cd ThreadPool
java Server  
# Output: "Server is listening on port 8010"

# Test with multi-threaded client (100 connections vs 10 threads!)
cd ../Multithread
java Client
# Watch how 10 server threads efficiently handle 100 client connections
# Some clients will queue while others are being processed
```

### File Reading Server Testing
```bash
# Create test data
echo -e "Line 1: Hello World\nLine 2: Java Server\nLine 3: File Reading Demo" > data.txt

# Start server
cd Fileread
java Server
# Output: "Server is listening on port 8010"

# Test with custom client
java Client
```

### Ultimate Performance Testing
**Test Thread Pool efficiency with Multi-threaded Client:**
```bash
# Terminal 1: Start Thread Pool Server (10 threads)
cd ThreadPool && java Server

# Terminal 2: Launch 100 concurrent clients against 10-thread server
cd Multithread && java Client

# Watch how thread pool manages 100 clients with only 10 threads!
# This demonstrates the power of thread pooling vs unlimited thread creation
```

### Load Testing
**⚠️ Important**: All servers use **port 8010** - only run one at a time!

**Single-Threaded Server**: Sequential testing only
```bash
# Test one client at a time (others will be blocked)
java Client
# Start another client - it will wait until first client completes
```

**Multi-Threaded Server**: Built-in load testing!
```bash
# The Client.java automatically creates 100 concurrent connections
java Client  # This will spawn 100 threads connecting to server
```

**Thread Pool Server**: Controlled concurrency testing
```bash
# Start server (only 10 threads available)
java Server

# Test with clients from any other implementation
cd ../Multithread
java Client  # This will test 100 clients against 10 server threads!
# Watch how thread pool manages the load efficiently
```

### File Reading Server Testing
```bash
# Create test data
echo -e "Line 1: Hello World\nLine 2: Java Server\nLine 3: File Reading Demo" > data.txt

# Start server
java Server

# Test with multiple clients
for i in {1..5}; do (java Client &); done
```

## 📝 Code Examples

### Server Response Variations
```java
// Single-Thread & Multi-Thread:
"Hello from the server"

// Thread Pool Server:
"Hello from server /127.0.0.1"  // Includes client IP

// File Reading Server:
"Line 1: Hello World"           // Sends entire file content
"Line 2: Java Server"           // Line by line
"Line 3: File Reading Demo"
```

### Threading Model Comparison
```java
// Single-Threaded: 1 connection at a time
Client 1: Connect → Process → Close
Client 2: Wait... → Connect → Process → Close

// Multi-Threaded: Create thread per client
Client 1-100: Each gets own thread (100 threads created)

// Thread Pool: Reuse fixed threads
Client 1-10: Get threads 1-10 immediately
Client 11-100: Queue and wait for available threads (reuse 1-10)
```

### File Reading Server Flow
```java
// Startup: Load data.txt → Create 100-thread pool → Listen on 8010
// Per client: Get thread from pool → Send all file lines → Return thread
```

### Simple HTTP Request
```java
// Example client request
GET / HTTP/1.1
Host: localhost:8080
Connection: close
```

### Server Response
```java
// Example server response
HTTP/1.1 200 OK
Content-Type: text/html
Content-Length: 13

Hello, World!
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📚 Learning Objectives

This project helps understand:
- Socket programming in Java
- Thread management and concurrency
- Client-server architecture
- HTTP protocol basics
- Performance optimization techniques
- Resource management in server applications

## 🐛 Common Issues & Solutions

### Port Already in Use
```bash
# Kill process using port 8010 (File Reading Server)
sudo lsof -ti:8010 | xargs kill -9

# For other ports
sudo lsof -ti:<PORT_NUMBER> | xargs kill -9
```

### Permission Denied
```bash
# Use ports above 1024 or run with sudo (not recommended)
```

### OutOfMemoryError
- Reduce thread pool size
- Increase JVM heap size: `java -Xmx512m Server`

## 📖 Additional Resources

- [Java Socket Programming Tutorial](https://docs.oracle.com/javase/tutorial/networking/sockets/)
- [HTTP Protocol Specification](https://tools.ietf.org/html/rfc2616)
- [Java Concurrency in Practice](https://jcip.net/)

## 📄 License

This project is open source and available under the [MIT License](LICENSE).

## 👨‍💻 Author

**Priyanshu** - [GitHub Profile](https://github.com/dev-priyanshu15)

---

⭐ **Star this repository if you found it helpful!**

## 🔮 Future Enhancements

- [ ] Add HTTPS support
- [ ] Implement HTTP/2 protocol
- [ ] Add request routing
- [ ] Include logging framework
- [ ] Add configuration file support
- [ ] Implement caching mechanisms
- [ ] Add WebSocket support
