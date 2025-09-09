from deepface import DeepFace

def recognize_face(image_path):
    try:
        # Compare against database images
        result = DeepFace.find(img_path=image_path, db_path="uploads", enforce_detection=False)
        if len(result) > 0 and len(result[0]) > 0:
            return result[0]['identity'][0]
        else:
            return "Unknown"
    except Exception as e:
        return "Error: " + str(e)
