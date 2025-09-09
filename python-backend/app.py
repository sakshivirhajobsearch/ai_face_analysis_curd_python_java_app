from flask import Flask, request, render_template_string
from deepface import DeepFace
import base64

app = Flask(__name__)

HTML_TEMPLATE = """
<!DOCTYPE html>
<html>
<head>
    <title>Face Analysis</title>
</head>
<body>
    <h1>Upload Image for Face Analysis</h1>
    <form method="POST" action="/" enctype="multipart/form-data">
      <input type="file" name="image" required />
      <button type="submit">Analyze Face</button>
    </form>

    {% if image_data %}
        <h2>Uploaded Image:</h2>
        <img src="data:image/jpeg;base64,{{ image_data }}" width="300"/>

        <h2>Analysis Results:</h2>
        <ul>
            <li><strong>Age:</strong> {{ result.age }}</li>
            <li><strong>Gender:</strong> {{ result.gender }}</li>
            <li><strong>Emotion:</strong> {{ result.emotion }}</li>
            <li><strong>Race:</strong> {{ result.race }}</li>
        </ul>
    {% endif %}

    {% if error %}
        <p style="color:red;"><strong>Error:</strong> {{ error }}</p>
    {% endif %}
</body>
</html>
"""

@app.route("/", methods=["GET", "POST"])
def index():
    if request.method == "POST":
        if "image" not in request.files:
            return render_template_string(HTML_TEMPLATE, error="No image provided")
        
        image_file = request.files["image"]
        
        try:
            # Analyze the face (returns a list)
            analysis_list = DeepFace.analyze(
                img_path=image_file,
                actions=["age", "gender", "emotion", "race"]
            )

            # DeepFace returns a list with one dict
            analysis = analysis_list[0] if isinstance(analysis_list, list) else analysis_list

            # Convert image to base64 for display
            image_file.seek(0)
            image_data = base64.b64encode(image_file.read()).decode("utf-8")

            # Extract main results
            result = {
                "age": analysis["age"],
                "gender": analysis["gender"],
                "emotion": max(analysis["emotion"], key=analysis["emotion"].get),
                "race": max(analysis["race"], key=analysis["race"].get)
            }

            return render_template_string(HTML_TEMPLATE, image_data=image_data, result=result)

        except Exception as e:
            return render_template_string(HTML_TEMPLATE, error=str(e))

    return render_template_string(HTML_TEMPLATE)

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000, debug=True)
