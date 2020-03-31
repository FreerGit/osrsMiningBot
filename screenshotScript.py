from PIL import ImageGrab
import win32gui
import threading
import time
id = win32gui.FindWindow(None, "RuneLite - fastminer99")

win32gui.SetForegroundWindow(id)
bbox = win32gui.GetWindowRect(id)
def background_calculation():
    for i in range(0,200):
        img = ImageGrab.grab(bbox)
        img.save("{:d}.png".format(i))
        time.sleep(2)

def main():
    thread = threading.Thread(target=background_calculation)
    thread.start()

    # TODO: wait here for the result to be available before continuing!


if __name__ == '__main__':
    main()
