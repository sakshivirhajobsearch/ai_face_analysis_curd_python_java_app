from flask import Flask, jsonify, request

app = Flask(__name__)

# Sample in-memory "database"
data_store = [
    {"id": 1, "name": "Item One"},
    {"id": 2, "name": "Item Two"}
]

# Home route
@app.route('/')
def home():
    return "Welcome to the Flask CRUD API!"

# Get all data
@app.route('/api/data', methods=['GET'])
def get_all_data():
    return jsonify(data_store)

# Get single data by ID
@app.route('/api/data/<int:data_id>', methods=['GET'])
def get_data_by_id(data_id):
    item = next((d for d in data_store if d["id"] == data_id), None)
    if item:
        return jsonify(item)
    return jsonify({"error": "Item not found"}), 404

# Create new data
@app.route('/api/data', methods=['POST'])
def create_data():
    new_data = request.json
    new_data['id'] = len(data_store) + 1
    data_store.append(new_data)
    return jsonify(new_data), 201

# Update data
@app.route('/api/data/<int:data_id>', methods=['PUT'])
def update_data(data_id):
    item = next((d for d in data_store if d["id"] == data_id), None)
    if not item:
        return jsonify({"error": "Item not found"}), 404
    updated_data = request.json
    item.update(updated_data)
    return jsonify(item)

# Delete data
@app.route('/api/data/<int:data_id>', methods=['DELETE'])
def delete_data(data_id):
    global data_store
    data_store = [d for d in data_store if d["id"] != data_id]
    return jsonify({"message": f"Item {data_id} deleted"}), 200

if __name__ == '__main__':
    app.run(debug=True)
